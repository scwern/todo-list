function saveTask(id) {
    const description = document.getElementById(`description_${id}`).value;
    const status = document.getElementById(`status_${id}`).value;

    $.ajax({
        url: `/${id}`,
        type: 'POST',
        data: {
            description: description,
            status: status
        },
        success: function (response) {
            alert("Task saved successfully!");
            location.reload();
        },
        error: function () {
            alert("Error saving task.");
        }
    });

}
function delete_task(id) {
    if (confirm("Are you sure you want to delete this task?")) {
        $.ajax({
            type: "DELETE",
            url: `/${id}`,
            success: function () {
                alert("Task deleted successfully!");
                location.reload();
            },
            error: function () {
                alert("Error deleting task.");
            }
        });
    }
}
function createTask() {
    const description = prompt("Enter the task description:");
    if (!description) return;

    let status = "IN_PROGRESS";

    $.ajax({
        type: "POST",
        url: "/tasks",
        contentType: "application/json",
        data: JSON.stringify({
            description: description,
            status: status
        }),
        success: function () {
            alert("Task created successfully!");
            location.reload();
        },
        error: function () {
            alert("Error creating task.");
        }
    });
}

function openEditModal(taskId) {
    document.getElementById('modalDescription').value = document.getElementById(`description_${taskId}`).value;

    let modal = new bootstrap.Modal(document.getElementById('editDescriptionModal'));
    modal.show();

    document.getElementById('saveDescription').onclick = function () {
        saveTaskDescription(taskId);
    };
}

function saveTaskDescription(id) {
    const newDescription = document.getElementById('modalDescription').value;
    const status = document.getElementById(`status_${id}`).value;

    $.ajax({
        url: `/${id}`,
        type: 'POST',
        data: {
            description: newDescription,
            status: status
        },
        success: function (response) {
            document.getElementById(`description_${id}`).value = newDescription;
            $('#editDescriptionModal').modal('hide');
            alert("Task description updated successfully!");
        },
        error: function () {
            alert("Error saving task description.");
        }
    });
}



